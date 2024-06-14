from dataclasses import dataclass
from ..extensions import db

@dataclass
class UserModel(db.Model):
    __tablename__ = 'User'

    id : int
    UserName : str
    UserEmail: str
    UserPass : str

    id          = db.Column(db.Integer, Primary_key=True)
    UserName    = db.Column(db.String(100),nullable=False)
    UserEmail   = db.Column(db.String(100),nullable=False)
    UserPass    = db.Column(db.String(100),nullable=False)