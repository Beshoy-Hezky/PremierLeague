
from bs4 import BeautifulSoup
import pandas as pd
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
import time

options = Options()
options.add_argument('--headless')
options.add_argument('--disable-gpu')
options.add_argument('--window-size=1920x1080')
# Create a Service object with the path to chromedriver this is done to configure the path without adding it to environment paths in windows
service = Service("C:/Users/besho/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe")

driver = webdriver.Chrome(service=service, options=options)

all_teams = []

driver.get('https://fbref.com/en/comps/9/Premier-League-Stats')
time.sleep(5)  # allow JavaScript to load
html = driver.page_source

soup = BeautifulSoup(html, 'lxml')
table = soup.find_all('table', class_='stats_table')[0]

links = table.find_all('a')
links = [l.get("href") for l in links]
links = [l for l in links if '/squads/' in l]
team_urls = [f"https://fbref.com{l}" for l in links]

for team_url in team_urls:
    team_name = team_url.split("/")[-1].replace("-Stats", "")

    driver.get(team_url)
    time.sleep(5)
    data = driver.page_source
    soup = BeautifulSoup(data, 'lxml')

    stats = soup.find_all('table', class_='stats_table')[0]

    team_data = pd.read_html(str(stats))[0]
    team_data["Team"] = team_name
    all_teams.append(team_data)
    time.sleep(5)

driver.quit()

stat_df = pd.concat(all_teams)
stat_df.to_csv("stats.csv")
