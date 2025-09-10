# File: terraform/variables.tf
variable "project_id" {
  description = "The GCP project ID."
  type        = string
}

variable "region" {
  description = "The GCP region to deploy resources in."
  type        = string
  default     = "us-central1"
}

variable "gke_cluster_name" {
  description = "The name for the GKE cluster."
  type        = string
  default     = "insurance-platform-cluster"
}

variable "artifact_registry_name" {
  description = "The name for the Artifact Registry repository."
  type        = string
  default     = "insurance-app-images"
}