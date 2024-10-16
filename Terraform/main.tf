provider "aws" {
  region = "us-east-1"
}

# Fetch the pre-existing IAM role
data "aws_iam_role" "LabRole" {
  name = "LabRole"  # Name of your pre-existing role
}

resource "aws_vpc" "pipeline_vpc" {
  cidr_block = "10.0.0.0/16"

  tags = {
    Name = "pipeline-vpc"
  }
}

resource "aws_subnet" "pipeline_subnet" {
  count = 2
  vpc_id                  = aws_vpc.pipeline_vpc.id
  cidr_block              = cidrsubnet(aws_vpc.pipeline_vpc.cidr_block, 8, count.index)
  availability_zone       = element(["us-east-1a", "us-east-1b"], count.index)
  map_public_ip_on_launch = true

  tags = {
    Name = "pipeline-subnet-${count.index}"
  }
}

resource "aws_internet_gateway" "pipeline_igw" {
  vpc_id = aws_vpc.pipeline_vpc.id

  tags = {
    Name = "pipeline-igw"
  }
}

resource "aws_route_table" "pipeline_route_table" {
  vpc_id = aws_vpc.pipeline_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.pipeline_igw.id
  }

  tags = {
    Name = "pipeline-route-table"
  }
}

resource "aws_route_table_association" "a" {
  count          = 2
  subnet_id      = aws_subnet.pipeline_subnet[count.index].id
  route_table_id = aws_route_table.pipeline_route_table.id
}

resource "aws_security_group" "pipeline_cluster_sg" {
  vpc_id = aws_vpc.pipeline_vpc.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "pipeline-cluster-sg"
  }
}

resource "aws_security_group" "pipeline_node_sg" {
  vpc_id = aws_vpc.pipeline_vpc.id

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "pipeline-node-sg"
  }
}

resource "aws_eks_cluster" "pipeline" {
  name     = "pipeline-cluster"
  role_arn = data.aws_iam_role.LabRole.arn  # Reference the existing IAM role

  vpc_config {
    subnet_ids         = aws_subnet.pipeline_subnet[*].id
    security_group_ids = [aws_security_group.pipeline_cluster_sg.id]
  }
}

resource "aws_eks_node_group" "pipeline" {
  cluster_name    = aws_eks_cluster.pipeline.name
  node_group_name = "pipeline-node-group"
  node_role_arn   = data.aws_iam_role.LabRole.arn  # Reference the existing IAM role
  subnet_ids      = aws_subnet.pipeline_subnet[*].id

  scaling_config {
    desired_size = 3
    max_size     = 3
    min_size     = 3
  }

  instance_types = ["t2.large"]
}
