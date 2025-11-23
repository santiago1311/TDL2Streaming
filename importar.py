import sqlite3
import pandas as pd
#Script desarrollado en python para importar datos de un archivo csv a una base de datos en SQLite3
#en conn se debe reemplazar por el nombre del archivo de la base de datos y 
#en df por el nombre del archivo csv, este script asume que estan
#el script y los dos archivos en la misma carpeta
#para ejecutarlo se debe ingresar en un terminal python3 import.py
conn = sqlite3.connect("appstreaming.db")
cursor = conn.cursor()

df = pd.read_csv("movies_database.csv")

df = df.rename(columns={
    "Title": "TITULO",
    "Overview": "RESUMEN",
    "Genre": "GENERO",
    "Release_Date": "FECHA_ESTRENO",
    "Popularity": "POPULARIDAD",
    "Vote_Count": "VOTOS",
    "Vote_Average": "PUNTAJE",
    "Original_Language": "IDIOMA_ORIGINAL",
    "Poster_Url": "POSTER"
})

columnas_csv = {
    "FECHA_ESTRENO": "TEXT",
    "TITULO": "TEXT",
    "RESUMEN": "TEXT",
    "POPULARIDAD": "REAL",
    "VOTOS": "INTEGER",
    "PUNTAJE": "REAL",
    "IDIOMA_ORIGINAL": "TEXT",
    "GENERO": "TEXT",
    "POSTER": "TEXT"
}

cursor.execute("PRAGMA table_info(PELICULA)")
existentes = {fila[1].upper() for fila in cursor.fetchall()}

for columna, tipo in columnas_csv.items():
    if columna.upper() not in existentes:
        print(f"→ Agregando columna {columna}")
        cursor.execute(f"ALTER TABLE PELICULA ADD COLUMN {columna} {tipo}")

conn.commit()

for _, row in df.iterrows():
    cursor.execute("""
        INSERT INTO PELICULA (
            TITULO,
            RESUMEN,
            GENERO,
            FECHA_ESTRENO,
            POPULARIDAD,
            VOTOS,
            PUNTAJE,
            IDIOMA_ORIGINAL,
            POSTER
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    """, (
        row.get("TITULO"),
        row.get("RESUMEN"),
        row.get("GENERO"),
        row.get("FECHA_ESTRENO"),
        row.get("POPULARIDAD"),
        row.get("VOTOS"),
        row.get("PUNTAJE"),
        row.get("IDIOMA_ORIGINAL"),
        row.get("POSTER")
    ))

conn.commit()
conn.close()

print("\nIMPORTACIÓN COMPLETADA — TABLA PELICULA ACTUALIZADA CORRECTAMENTE")

