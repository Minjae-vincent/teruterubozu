cd ./backend

# remove static folder if exists
mvn clean

# make static folder where frontend build will be copied
mkdir -p target/classes/static

cd ../frontend

# install frontend dependencies
npm install

# build frontend
# npm run build -- --dest ../backend/target/classes/static
npm run build -- --dest ../backend/src/main/resources/static

cd ../backend

# build backend
mvn package -DskipTests