from datetime import date
from firebase_admin import auth
from flask import Blueprint, request
from ..extensions import db
from ..decorator import authenticated_only
from ..models.dashboard import *

dashboard = Blueprint("dashboards", __name__)

@dashboard.route("/dashboard/<int:id>/details", methods=["GET"])
@authenticated_only
def get_menu_detail(id):
    menu_detail = db.session.query(MenuDetails) \
        .filter_by(menu_id=id).first()
    if not menu_detail:
        return {"message": f"Menu with id {id} doesn't exist"}, 404
    
    user_id = request.user.get("uid")
    return {"data": menu_detail.serialize(user_id, id)}, 200

@dashboard.route("/menu-categories", methods=["GET"])
def get_menu_categories():
    categories = db.session.query(MenuCategory) \
        .order_by(MenuCategory.name.asc()).all()
    return {"data": categories}, 200