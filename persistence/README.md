  neo4j:
    image: neo4j:5.26.5-community-bullseye
    ports:
      - "7474:7474"
      - "7687:7687"
    environment:
      NEO4J_AUTH: "neo4j/neo4j"
    volumes:
      - "C:/Users/Leander Self/neo4j/data:/data"
      - "C:/Users/Leander Self/neo4j/import:/var/lib/neo4j/import"
      - "C:/Users/Leander Self/neo4j/logs:/logs"
      - "C:/Users/Leander Self/neo4j/conf:/conf"
      - "C:/Users/Leander Self/neo4j/plugins:/plugins"
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: admin
      MYSQL_DATABASE: mydb
      MYSQL_USER: admin     
      MYSQL_PASSWORD: admin
    ports:
      - "3306:3306"
    volumes:
      - "C:/Users/Leander Self/mysql/data:/var/lib/mysql"