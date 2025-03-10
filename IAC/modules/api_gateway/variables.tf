variable "alb_dns_name" {
  description = "DNS del ALB para configurar las integraciones del API Gateway"
  type        = string
}

variable "region" {
  description = "Región de AWS"
  type        = string
  default     = "us-east-1"
}