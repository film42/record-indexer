

CREATE TABLE "fields" (
"id" INTEGER PRIMARY KEY AUTOINCREMENT,
"title" TEXT NOT NULL,
"x_coord" INTEGER NOT NULL,
"width" INTEGER NOT NULL,
"help_html" TEXT NOT NULL,
"known_data" TEXT,
"position" INTEGER NOT NULL,
"project_id" INTEGER REFERENCES "projects"("id")
);


CREATE TABLE "images" (
"id" INTEGER PRIMARY KEY AUTOINCREMENT,
"file" TEXT NOT NULL,
"project_id" INTEGER REFERENCES "projects"("id")
);


CREATE TABLE "projects" (
"id" INTEGER PRIMARY KEY AUTOINCREMENT,
"title" TEXT NOT NULL,
"records_per_image" INTEGER NOT NULL,
"first_y_coord" INTEGER NOT NULL,
"record_height" INTEGER NOT NULL
);


CREATE TABLE "records" (
"id" INTEGER PRIMARY KEY AUTOINCREMENT,
"position" INTEGER NOT NULL,
"image_id" INTEGER REFERENCES "images"("id")
);


CREATE TABLE "users" (
"id" INTEGER PRIMARY KEY AUTOINCREMENT,
"username" TEXT UNIQUE NOT NULL,
"password" TEXT NOT NULL,
"first_name" TEXT NOT NULL,
"last_name" TEXT NOT NULL,
"email" TEXT NOT NULL,
"indexed_records" INTEGER NOT NULL,
"image_id" INTEGER REFERENCES "images"("id")
);


CREATE TABLE "values" (
"id" INTEGER PRIMARY KEY AUTOINCREMENT,
"value" TEXT,
"type" TEXT,
"position" INTEGER,
"record_id" INTEGER REFERENCES "records"("id")
);
