#!/usr/bin/env bash
# Lance le backend Spring Boot (Gradle bootRun). Detecte un JDK 25 requis par la toolchain.
set -euo pipefail

cd "$(dirname "$0")/../backend"

find_jdk25() {
  if [ -n "${JAVA_HOME:-}" ] && "$JAVA_HOME/bin/java" -version 2>&1 | grep -q 'version "25'; then
    echo "$JAVA_HOME"; return 0
  fi
  for d in "$HOME/.jdks"/*25* \
           "/c/Program Files/Eclipse Adoptium"/jdk-25* \
           "/c/Program Files/Java"/jdk-25* \
           "/c/Program Files/Microsoft/jdk-25"* \
           "/usr/lib/jvm"/*25*; do
    [ -x "$d/bin/java" ] && { echo "$d"; return 0; }
  done
  return 1
}

if JDK25="$(find_jdk25)"; then
  export JAVA_HOME="$JDK25"
  echo "[start-backend] JAVA_HOME=$JAVA_HOME"
else
  echo "[start-backend] JDK 25 introuvable : Gradle tentera de provisionner la toolchain." >&2
fi

exec ./gradlew bootRun
