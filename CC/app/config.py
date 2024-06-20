import os

class Config:
    SECRET_KEY = os.environ.get('SECRET_KEY') or 'your_secret_key'
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    JWT_SECRET_KEY = os.environ.get('JWT_SECRET_KEY') or 'your_jwt_secret_key'
    UPLOAD_FOLDER = os.environ.get('UPLOAD_FOLDER') or 'uploads/'

    # Cloud SQL configuration
    CLOUD_SQL_USERNAME = os.environ.get('CLOUD_SQL_USERNAME')
    CLOUD_SQL_PASSWORD = os.environ.get('CLOUD_SQL_PASSWORD')
    CLOUD_SQL_DATABASE_NAME = os.environ.get('CLOUD_SQL_DATABASE_NAME')
    CLOUD_SQL_CONNECTION_NAME = os.environ.get('CLOUD_SQL_CONNECTION_NAME')

    # Local development configuration
    if os.getenv('GAE_ENV', '').startswith('standard'):
        # Running on Google App Engine Standard or Cloud Run
        SQLALCHEMY_DATABASE_URI = (
            f'postgresql+psycopg2://{CLOUD_SQL_USERNAME}:{CLOUD_SQL_PASSWORD}@/{CLOUD_SQL_DATABASE_NAME}?host=/cloudsql/{CLOUD_SQL_CONNECTION_NAME}'
        )
    else:
        # Running locally
        SQLALCHEMY_DATABASE_URI = os.environ.get('DATABASE_URL') or 'sqlite:///site.db'
