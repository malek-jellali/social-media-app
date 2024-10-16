output "cluster_id" {
  value = aws_eks_cluster.pipeline.id
}

output "node_group_id" {
  value = aws_eks_node_group.pipeline.id
}

output "vpc_id" {
  value = aws_vpc.pipeline_vpc.id
}

output "subnet_ids" {
  value = aws_subnet.pipeline_subnet[*].id
}