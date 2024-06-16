import requests
from flask import Blueprint, request
from os import getenv
from ..extensions import db
from ..decorator import authenticated_only
from ..models.favorite_recipe import *
from ..models.dashboard import *

favorite_recipe = Blueprint("favorite_recipe", __name__)

