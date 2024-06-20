from flask import Blueprint, request, jsonify, current_app
from flask_jwt_extended import jwt_required, get_jwt_identity, get_jwt, unset_jwt_cookies
from app import db
from app.models.FoodMenu import FoodMenu
from app.models.favorite_menu import FavoriteMenu
from app.models.user import User

auth_bp = Blueprint('auth', __name__)

# Endpoint untuk Register
@auth_bp.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    username = data.get('username')
    email = data.get('email')
    password = data.get('password')

    if User.query.filter_by(username=username).first() or User.query.filter_by(email=email).first():
        return jsonify({'message': 'User already exists'}), 400

    user = User(username=username, email=email)
    user.set_password(password)
    db.session.add(user)
    db.session.commit()

    return jsonify({'message': 'User registered successfully'}), 201

# Endpoint untuk Login
@auth_bp.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    email = data.get('email')
    password = data.get('password')

    user = User.query.filter((User.email == email)).first()

    if user and user.check_password(password):
        token = user.create_token()
        return jsonify({'token': token}), 200
    else:
        return jsonify({'message': 'Invalid credentials'}), 401

# Endpoint untuk menambahkan menu baru
@auth_bp.route('/menu/add', methods=['POST'])
@jwt_required()
def add_menu():
    current_user_id = get_jwt_identity()

    data = request.get_json()
    menu_name = data.get('menu_name')
    image_url = data.get('image_url')
    description = data.get('description')
    ingredients = data.get('ingredients', [])
    instructions = data.get('instructions', [])
    category = data.get('category')

    # Create new FoodMenu object
    new_menu = FoodMenu(
        menu_name=menu_name,
        image_url=image_url,
        description=description,
        ingredients=ingredients,
        instructions=instructions,
        category=category,
        author_id=current_user_id
    )

    try:
        # Add new menu to the database
        db.session.add(new_menu)
        db.session.commit()
        return jsonify({'message': 'Menu added successfully'}), 201
    except Exception as e:
        db.session.rollback()
        return jsonify({'message': 'Failed to add menu', 'error': str(e)}), 500

# Endpoint untuk menambahkan menu ke favorite
@auth_bp.route('/menu/<int:id>/favorite', methods=['POST'])
@jwt_required()
def add_to_favorite(id):
    current_user_id = get_jwt_identity()

    menu = FoodMenu.query.get(id)
    if not menu:
        return jsonify({'message': 'Menu not found'}), 404

    # Check if the menu already in favorite
    existing_favorite = FavoriteMenu.query.filter_by(menu_name=menu.menu_name, user_id=current_user_id).first()
    if existing_favorite:
        return jsonify({'message': 'Menu already in favorite'}), 400

    # Add menu to favorite
    favorite_menu = FavoriteMenu(
        menu_name=menu.menu_name,
        image_url=menu.image_url,
        menu_rating=None,  # Set initial rating to None or default
        is_favorite=True,
        user_id=current_user_id
    )

    try:
        db.session.add(favorite_menu)
        db.session.commit()
        return jsonify({'message': 'Menu added to favorites successfully'}), 201
    except Exception as e:
        db.session.rollback()
        return jsonify({'message': 'Failed to add menu to favorites', 'error': str(e)}), 500

# Endpoint untuk melihat menu favorite
@auth_bp.route('/favorite_menus', methods=['GET'])
@jwt_required()
def get_favorite_menus():
    current_user_id = get_jwt_identity()

    favorite_menus = FavoriteMenu.query.filter_by(user_id=current_user_id).all()

    favorite_menus_list = []
    for favorite_menu in favorite_menus:
        favorite_menus_list.append({
            'menu_name': favorite_menu.menu_name,
            'image_url': favorite_menu.image_url,
            'menu_rating': favorite_menu.menu_rating,
            'is_favorite': favorite_menu.is_favorite
        })

    return jsonify({'favorite_menus': favorite_menus_list}), 200

# Endpoint untuk melihat Detail Menu
@auth_bp.route('/menu/<int:id>', methods=['GET'])
@jwt_required(optional=True)  # Pengguna dapat melihat detail menu tanpa login
def get_menu_detail(id):
    menu = FoodMenu.query.get(id)

    if not menu:
        return jsonify({'message': 'Menu not found'}), 404

    author = User.query.get(menu.author_id)
    if not author:
        return jsonify({'message': 'Author not found for this menu'}), 404

    current_user_id = get_jwt_identity()
    is_favorite = False

    if current_user_id:
        # Cek apakah menu ada di favorit pengguna saat ini
        from app.models.favorite_menu import FavoriteMenu
        favorite = FavoriteMenu.query.filter_by(menu_name=menu.menu_name, user_id=current_user_id).first()
        if favorite:
            is_favorite = True

    # Konversi rating jika diperlukan, misalnya dari format 5/5
    # Asumsikan nilai rating didapat dari pengguna dalam format 5/5
    menu_rating = None  # Anda bisa menambahkan logika untuk menghitung rating sesuai kebutuhan

    menu_detail = {
        'menu_name': menu.menu_name,
        'image_url': menu.image_url,
        'description': menu.description,
        'ingredients': menu.ingredients,
        'instructions': menu.instructions,
        'menu_rating': menu_rating,
        'author_id': menu.author_id,
        'author_name': author.username,  # Misalnya, asumsikan nama pengguna diambil dari model User
        'is_favorite': is_favorite
    }

    return jsonify({'menu_detail': menu_detail}), 200

# Endpoint untuk mencari menu makanan dengan teks
@auth_bp.route('/menu/search', methods=['GET'])
@jwt_required(optional=True)  # Optional JWT, bisa diakses tanpa login
def search_menu():
    search_term = request.args.get('q', '')  # Ambil parameter 'q' dari query string

    if not search_term.strip():  # Pastikan search_term tidak kosong
        return jsonify({'message': 'Missing search term'}), 400

    # Query menu berdasarkan nama yang mengandung search_term
    menus = FoodMenu.query.filter(FoodMenu.menu_name.ilike(f'%{search_term}%')).all()

    if not menus:
        return jsonify({'message': 'No menus found'}), 404

    # Format data hasil pencarian
    search_results = []
    for menu in menus:
        menu_detail = {
            'menu_name': menu.menu_name,
            'image_url': menu.image_url,
            #'menu_rating': menu.menu_rating  # Anda perlu menambahkan logika untuk menghitung rating jika belum ada
        }
        search_results.append(menu_detail)

    return jsonify({'search_results': search_results}), 200

# Endpoint untuk logout
@auth_bp.route('/logout', methods=['POST'])
@jwt_required()
def logout():
    resp = jsonify({'message': 'Logout successful'})
    unset_jwt_cookies(resp)  # hapus token JWT dari cookies atau header response
    return resp, 200