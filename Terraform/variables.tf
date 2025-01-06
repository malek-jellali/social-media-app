variable "ssh_key_name" {
  description = "The name of the SSH key pair to use for the EC2 instances."
  type        = string
  default     = "cicd"
}
# Variables
variable "region" {
  default = "us-east-1"
}