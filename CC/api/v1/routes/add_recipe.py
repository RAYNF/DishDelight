from firebase_admin import auth
from flask import Blueprint, request
from os import getenv
from ..extensions import db
from ..decorator import authenticated_only
from ..helper import get_bucket_storage
from ..models.dashboard import Dashboard
from ..models.add_recipe import *

add_recipe = Blueprint("add_recipe", __name__)

@add_recipe.route("/add_recipe", methods = ['POST'])
@authenticated_only
def create_recipe():
    data = request.form if request.content_type.startswith("multipart/form-data") else request.json

    name_recipes = data.get("name_recipes")
    description = data.get("description")
    menu_id = data.get("menu_id")

    if not name_recipes or not description:
        return {"message": "Name menu and description are required"}, 400
    
    image = request.files.get("image")
    image_url = None
    
    if image:
        if not image.content_type.startswith("image/"):
            return {"message": "File is not a valid image"}, 400
        
        bucket = get_bucket_storage(getenv("BUCKET_NAME"))
        blob = bucket.blob(f"add_recipe/{image.filename}")
        
        blob.upload_from_file(image)
        blob.make_public()
        image_url = blob.public_url