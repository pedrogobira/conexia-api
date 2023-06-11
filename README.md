# conexia-api

Para rodar esse projeto você vai precisar de um banco de dados PostgreSQL ativo. As credenciais necessárias estão em [application.properties](src%2Fmain%2Fresources%2Fapplication.properties).

Recomendado utilizar um container docker para isso. Segue comando para rodar um simples container docker do PostgreSQL:

``` sh
sudo docker run --name conexia-postgres -p 5432:5432 -e POSTGRES_PASSWORD=root -d postgres 
```

O arquivo do schema do postman pode ser encontrado nesse repositório com o nome de `CONEXIA.postman_collection.json`.
