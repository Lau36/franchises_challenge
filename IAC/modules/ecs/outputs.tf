output "ecs_cluster_id" {
  description = "ID del cluster ECS creado"
  value       = aws_ecs_cluster.franchise_cluster.id
}