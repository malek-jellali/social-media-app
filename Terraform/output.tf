# Outputs
output "cluster_name" {
  description = "The name of the EKS Cluster"
  value       = aws_eks_cluster.socialmedia-cluster.name
}

output "cluster_endpoint" {
  description = "The endpoint for the EKS cluster"
  value       = aws_eks_cluster.socialmedia-cluster.endpoint
}

output "cluster_certificate_authority" {
  description = "The certificate authority data for the EKS cluster"
  value       = aws_eks_cluster.socialmedia-cluster.certificate_authority[0].data
}