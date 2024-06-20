"""Create favorite_menu table

Revision ID: d61a902842dc
Revises: 5913d04ff794
Create Date: 2024-06-19 14:33:25.092396

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = 'd61a902842dc'
down_revision = '5913d04ff794'
branch_labels = None
depends_on = None


def upgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.create_table('favorite_menu',
    sa.Column('id', sa.Integer(), nullable=False),
    sa.Column('menu_name', sa.String(length=100), nullable=False),
    sa.Column('image_url', sa.String(length=200), nullable=True),
    sa.Column('menu_rating', sa.Integer(), nullable=True),
    sa.Column('is_favorite', sa.Boolean(), nullable=True),
    sa.Column('user_id', sa.Integer(), nullable=False),
    sa.ForeignKeyConstraint(['user_id'], ['user.id'], ),
    sa.PrimaryKeyConstraint('id')
    )
    # ### end Alembic commands ###


def downgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_table('favorite_menu')
    # ### end Alembic commands ###