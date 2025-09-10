# File: terraform/outputs.tf
output "gke_cluster_name" {
  description = "The name of the GKE cluster."
  value       = google_container_cluster.primary.name
}

output "gke_cluster_endpoint" {
  description = "The endpoint for the GKE cluster."
  value       = google_container_cluster.primary.endpoint
  sensitive   = true
}

output "artifact_registry_location" {
  description = "The location of the Artifact Registry (e.g., us-central1-docker.pkg.dev)."
  value       = google_artifact_registry_repository.docker_repo.location
}

output "artifact_registry_url" {
  description = "The full URL for the Artifact Registry repository."
  value       = "${google_artifact_registry_repository.docker_repo.location}-docker.pkg.dev/${var.project_id}/${google_artifact_registry_repository.docker_repo.name}"
}