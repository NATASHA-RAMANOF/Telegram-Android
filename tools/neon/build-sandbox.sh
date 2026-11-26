#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/../.."
test "$(git branch --show-current)" = "feature/neon-sandbox" || { echo "Use sandbox branch only"; exit 1; }
test -z "$(git status --porcelain)" || { echo "Use a clean disposable checkout"; exit 1; }
: "${NEON_API_ID:?Set dedicated API ID}"
: "${NEON_API_HASH:?Set dedicated API hash}"
test -n "${ANDROID_HOME:-}" || { echo "Android SDK required"; exit 1; }
trap 'git restore -- TMessagesProj/src/main/java/org/telegram/messenger/BuildVars.java' EXIT
python3 tools/neon/configure_api.py
bash gradlew :TMessagesProj_App:assembleAfatDebug -PAPP_PACKAGE=com.gromanof.neon --no-daemon
