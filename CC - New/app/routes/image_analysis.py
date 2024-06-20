from flask import Blueprint, request, jsonify
from flask_jwt_extended import jwt_required, get_jwt_identity
import os
from werkzeug.utils import secure_filename
from PIL import Image
import numpy as np
import tensorflow as tf  # Asumsikan menggunakan TensorFlow sebagai framework ML
from app.models.FoodMenu import FoodMenu
from app.models.favorite_menu import FavoriteMenu

image_analysis_bp = Blueprint('image_analysis', __name__)

# Load the model once at the start (modify the path to your model)
MODEL_PATH = 'model/model.h5'
model = tf.keras.models.load_model(MODEL_PATH)

# Define the allowed extensions for image upload
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@image_analysis_bp.route('/upload', methods=['POST'])
@jwt_required()
def upload_image():
    if 'file' not in request.files:
        return jsonify({'message': 'No file part'}), 400
    file = request.files['file']
    if file.filename == '':
        return jsonify({'message': 'No selected file'}), 400
    if file and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        file_path = os.path.join('path_to_save_uploaded_files', filename)  # Modify the path
        file.save(file_path)

        # Process the image and predict
        image = Image.open(file_path)
        image = image.resize((224, 224))  # Resize image to the size expected by the model
        image = np.array(image) / 255.0
        image = np.expand_dims(image, axis=0)  # Add batch dimension

        prediction = model.predict(image)
        predicted_class = np.argmax(prediction, axis=1)[0]

        # Assuming you have a mapping from predicted_class to food menu names
        menu_name = map_predicted_class_to_menu_name(predicted_class)

        # Search for the menu in the database
        menu = FoodMenu.query.filter_by(menu_name=menu_name).first()

        if not menu:
            return jsonify({'message': 'Menu not found'}), 404

        current_user_id = get_jwt_identity()
        is_favorite = False

        if current_user_id:
            favorite = FavoriteMenu.query.filter_by(menu_name=menu.menu_name, user_id=current_user_id).first()
            if favorite:
                is_favorite = True

        menu_detail = {
            'menu_name': menu.menu_name,
            'category': menu.category,
            'is_favorite': is_favorite
        }

        return jsonify({'menu_detail': menu_detail}), 200
    else:
        return jsonify({'message': 'File type not allowed'}), 400

def map_predicted_class_to_menu_name(predicted_class):
    # This function should map the predicted class index to the corresponding menu name
    # You need to define this mapping based on your model's classes
    class_to_menu_name = {
        0: 'Nasi Goreng Special',
        1: 'Mie Goreng',
        # Add other mappings as necessary
    }
    return class_to_menu_name.get(predicted_class, 'Unknown Menu')
