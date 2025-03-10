output "ecr_repository_url" {
  description = "URL del repositorio ECR"
  value       = module.ecr.ecr_url
}

output "db_endpoint" {
  description = "Endpoint de la base de datos RDS"
  value       = module.rds.rds_endpoint
}

output "alb_dns_name" {
  description = "DNS del Application Load Balancer"
  value       = module.alb.alb_dns_name
}

output "ecs_cluster_id" {
  description = "ID del cluster ECS"
  value       = module.ecs.ecs_cluster_id
}

output "api_invoke_url" {
  description = "URL de invocación del API Gateway"
  value       = module.api_gateway.api_invoke_url
}