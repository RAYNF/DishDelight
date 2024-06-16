from dataclasses import dataclass
from firebase_admin import auth
from ..extensions import db


@dataclass
class add_recipe(db.Model):
    __tablename__ = "food"

    name_recipes    : str
    description     : str
    ingredients     : str
    

    menu_id: int   = db.Column(db.Integer, primary_key=True)
    nama_recipes   = db.Column(db.String(50))
    image_url: str = db.Column(db.String)
    description    = db.Column(db.String(100))
    ingredients    = db.Column(db.String(30))
    instructions   = db.Column(db.String(200))
    

