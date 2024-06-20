from app import db
from datetime import datetime

class FoodMenu(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    menu_name = db.Column(db.String(100), nullable=False)
    image_url = db.Column(db.String(200))
    description = db.Column(db.Text)
    ingredients = db.Column(db.JSON)
    instructions = db.Column(db.JSON)
    category = db.Column(db.String(50))
    author_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)

    author = db.relationship('User', backref=db.backref('menus', lazy=True))

    def __init__(self, menu_name, image_url, description, ingredients, instructions, category, author_id):
        self.menu_name = menu_name
        self.image_url = image_url
        self.description = description
        self.ingredients = ingredients
        self.instructions = instructions
        self.category = category
        self.author_id = author_id
