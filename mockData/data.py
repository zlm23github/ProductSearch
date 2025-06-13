import csv
import random
from faker import Faker

fake = Faker()
categories = ["Electronics", "Books", "Clothing", "Home", "Toys"]

with open('products.csv', 'w', newline='', encoding='utf-8') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(['title', 'description', 'price', 'category'])  # Header

    for i in range(1, 200001):  #
        title = fake.catch_phrase()
        description = fake.text(max_nb_chars=80)
        price = round(random.uniform(10, 2000), 2)
        category = random.choice(categories)
        writer.writerow([title, description, price, category])

print("products.csv generated!")
