from flask import Blueprint, jsonify
from flask_jwt_extended import jwt_required, get_jwt_identity
from sqlalchemy.sql import func
from app.models.FoodMenu import FoodMenu
from app.models.favorite_menu import FavoriteMenu
from app import db

homepage_bp = Blueprint('homepage', __name__)

@homepage_bp.route('/homepage', methods=['GET'])
@jwt_required()
def get_homepage():
    current_user_id = get_jwt_identity()
    
    menus = db.session.query(
        FoodMenu.menu_name,
        FoodMenu.image_url,
        func.avg(FavoriteMenu.menu_rating).label('menu_rating'),
        func.count(FavoriteMenu.is_favorite).filter(FavoriteMenu.is_favorite == True).label('is_favorite')
    ).outerjoin(FavoriteMenu, FoodMenu.menu_name == FavoriteMenu.menu_name)\
    .group_by(FoodMenu.menu_name, FoodMenu.image_url).all()

    recommendations = []
    for menu in menus:
        recommendations.append({
            'menu_name': menu.menu_name,
            'image_url': menu.image_url,
            'menu_rating': round(menu.menu_rating, 1) if menu.menu_rating else 0,
            'is_favorite': menu.is_favorite > 0
        })
    
    return jsonify({'recommendations': recommendations}), 200
