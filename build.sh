if [[ "$OSTYPE" == "linux-gnu" ]]; then
  export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 &&
  export PATH=$PATH:$JAVA_HOME/bin
fi
rm -rf package-lock.json node_modules target webpack.generated.js && npm install &&
mvn install -Pdirectory
