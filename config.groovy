environments {
    local {
        jdbc {
            url = 'jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=utf-8'
            username = 'root'
            password = 'root'
            maxActive = '5'
            initialSize = '1'
        }
    }
    prod {
        jdbc {
            url = 'jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=utf-8'
            username = 'root'
            password = 'Oracle12c'
            maxActive = '10'
            initialSize = '5'
        }
    }
}
