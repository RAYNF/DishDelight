from dataclasses import dataclass
from ..models.dashboard import Dashboard
from ..extensions import db

@dataclass
class Favorite(db.Model):
    __tablename__ = 'favorites'

    name_recipes: str

    id: int = db.Column(db.Integer, primary_key=True)
    name_recipes: str = db.Column(db.String(100), nullable=False)
    image_url: str = db.Column(db.String, nullable=False)
    details = db.relationship("MenuDetails", uselist=False)
    author = db.relationship("MenuAuthor", uselist=False)

    def serialize(self, user_id):
        tourism = db.session.query(MenuDetails) \
            .filter_by(user_id=user_id, favorite_id=self.id).first()
        return {
            "tourism": self,
            "is_favorite": tourism != None
        }
    
    @staticmethod
    def serialize_list(user_id, tourisms):
        return [tourism.serialize(user_id) for tourism in tourisms]

@dataclass
class MenuDetails(db.Model):
    __tablename__ = "menu_details"

    author_name: str

    id = db.Column(db.Integer, primary_key=True)
    author_id = db.Column(db.String, nullable=False)
    description: str = db.Column(db.Text, nullable=False)
    nutritions: str = db.Column(db.Text, nullable=False)
    ingredients: str = db.Column(db.Text, nullable=False)
    instructions: str = db.Column(db.Text, nullable=False)
    rating = db.Column(db.Integer, nullable=True)
    menu_id = db.Column(db.Integer, db.ForeignKey('dashboards.id'), nullable=False)

    @property
    def author_name(self):
        author = auth.get_user(uid=self.author_id)
        return author.display_name

@dataclass
class TourismFavorite(db.Model):
    __tablename__ = "tourism_favorites"

    tourism_id = db.Column(db.String(30), db.ForeignKey('tourisms.id'), primary_key=True)
    user_id = db.Column(db.String, primary_key=True)