
# 使用的时候更改maven中的包作用域

        <dependency>
            <groupId>org.flywaydb<****/groupId>
            <artifactId>flyway-core</artifactId>
            <version>6.2.4</version>
            <scope>provided</scope>
        </dependency>

更改为

        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <version>6.2.4</version>
        </dependency>