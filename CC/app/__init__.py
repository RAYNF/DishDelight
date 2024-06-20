from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate
from flask_bcrypt import Bcrypt
from flask_jwt_extended import JWTManager
from datetime import timedelta

db = SQLAlchemy()
migrate = Migrate()
bcrypt = Bcrypt()
jwt = JWTManager()

def create_app():
    app = Flask(__name__)
    app.config.from_object('app.config.Config')

    db.init_app(app)
    migrate.init_app(app, db)
    bcrypt.init_app(app)
    jwt.init_app(app)

    from app.models.FoodMenu import FoodMenu  # Pastikan model sudah diimpor
    from app.routes.auth import auth_bp
    from app.routes.image_analysis import image_analysis_bp
    from app.routes.homepage import homepage_bp

    app.register_blueprint(auth_bp, url_prefix='/auth')
    app.register_blueprint(image_analysis_bp, url_prefix='/image_analysis')
    app.register_blueprint(homepage_bp, url_prefix='/homepage')

    return app