# File: terraform/main.tf
provider "google" {
  project = var.project_id
  region  = var.region
}

# 1. VPC Network
resource "google_compute_network" "vpc_network" {
  name                    = "insurance-platform-vpc"
  auto_create_subnetworks = "true"
}

# 2. GKE Cluster
resource "google_container_cluster" "primary" {
  name     = var.gke_cluster_name
  location = var.region
  network  = google_compute_network.vpc_network.name

  initial_node_count = 1

  node_config {
    machine_type = "e2-medium"
    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
  }
}

# 3. Artifact Registry for Docker images
resource "google_artifact_registry_repository" "docker_repo" {
  location      = var.region
  repository_id = var.artifact_registry_name
  description   = "Docker repository for insurance platform microservices"
  format        = "DOCKER"
}