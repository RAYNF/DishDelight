h1 align="center">
  <br>
    <img src="assets/logoDishDelight.jpeg" alt="DishDelight" width="200">
  <br>
    DishDelight
  <br>
    <small style="font-size: 16px"><em>Create healthy food with ingredients around you</em></small>
</h1>

## Table of Contents
- [Table of Contents](#table-of-contents)
- [Architecture](#architecture)
- [Development](#development)
- [Deployment](#deployment)
- [Project Structure](#project-structure)
- [API Documentation](#api-documentations)
- [Cloud Computing Team](#cloud-computing-team)

## Architecture
The architecture of this project can be seen in the image below.
![Architecture](assets/)

## Development
- Clone this repository
```
https://github.com/RAYNF/DishDelight.git
```
- Install project dependencies
```
pip install -r requirements.txt
```
- Run the application
```
flask --app main run -p 5000
```

  ## Project Structure
```
├───app
│   ├───__init__.py
│   ├───config.py
│   ├───models
│   │   ├───FoodMenu.py
│   │   ├───__init__.py
│   │   ├───blacklist.py
│   │   ├───favorite_menu.py
│   │   └───user.py
│   ├───routes
│   │   ├───__init__.py
│   │   ├───auth.py
│   │   ├───homepage.py
│   │   └───image_analysis.py
├───assets
│   └───logoDishDelight.jpeg
├───migrations
│   ├───README
│   ├───alembic.ini
│   ├───env.py
│   ├───script.py.mako
│   ├───versions
│   │   ├───404510c04753_create_food_menu_table.py
│   │   ├───5913d04ff794_create_food_menu_table.py
│   │   ├───9cae1f60c8f6_initial_migration.py
│   │   └───d61a902842dc_create_favorite_menu_table.py
├───.dockerignore
├───.gitignore
├───Dockerfile
├───requirements.txt
└───run.py

```

## API Documentation
The details of the API documentation can be accessed at [here]([https://documenter.getpostman.com/view/2s93sc4spc](https://documenter.getpostman.com/view/36428624/2sA3XTfLhn)).

## Cloud Computing Team
| Name | Student ID | Contact |
| - | - | - |
| Eka Arya Pranata | C007D4KY0406  | <a href="linkedin.com/in/eka-arya-pranata-5a4132300"><img src="https://img.shields.io/badge/LinkedIn-0077B5?
| Nikolas Adi Kurniatmaja Sijabat  | C007D4KY0034  | <a href="https://www.linkedin.com/in/adinikolas/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?
