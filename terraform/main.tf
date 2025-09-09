resource "kubernetes_namespace" "customer" {
  metadata {
    name = "customer"
  }
}
