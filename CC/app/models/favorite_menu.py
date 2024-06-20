from app import db

class FavoriteMenu(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    menu_name = db.Column(db.String(100), nullable=False)
    image_url = db.Column(db.String(200))
    menu_rating = db.Column(db.Integer)
    is_favorite = db.Column(db.Boolean, default=False)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    user = db.relationship('User', backref=db.backref('favorites', lazy=True))

    def __init__(self, menu_name, image_url, menu_rating, is_favorite, user_id):
        self.menu_name = menu_name
        self.image_url = image_url
        self.menu_rating = menu_rating
        self.is_favorite = is_favorite
        self.user_id = user_id
