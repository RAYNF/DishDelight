from dataclasses import dataclass
from ..extensions import db

@dataclass
class Favorite(db.Model):
    __tablename__ = 'favorite'

    