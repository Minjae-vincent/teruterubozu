cd ./frontend
npm run build

mv ./dist/* ../backend/src/main/resources/static

cd ../backend

mvn package -DskipTests