provider "google" {
  project = var.project_id
  region  = var.region
}

resource "google_storage_bucket" "documents" {
  name     = "${var.project_id}-documents"
  location = var.region
}

resource "google_sql_database_instance" "mysql" {
  name             = "doc-mysql"
  database_version = "MYSQL_8_0"
  region           = var.region

  settings {
    tier = "db-f1-micro"
  }
}

resource "google_container_cluster" "primary" {
  name     = "doc-gke-cluster"
  location = var.region
  initial_node_count = 1
}
