"""Configure a disposable build checkout only; never commit generated BuildVars."""
import os
import re
from pathlib import Path

def main():
    app_id = os.environ.get("NEON_API_ID", "")
    app_hash = os.environ.get("NEON_API_HASH", "")
    if not re.fullmatch(r"[1-9][0-9]{0,9}", app_id) or int(app_id) > 2147483647:
        raise SystemExit("Missing or invalid NEON_API_ID")
    if not re.fullmatch(r"[0-9a-fA-F]{32}", app_hash):
        raise SystemExit("Missing or invalid NEON_API_HASH")
    path = Path(__file__).resolve().parents[2] / "TMessagesProj/src/main/java/org/telegram/messenger/BuildVars.java"
    source = path.read_text(encoding="utf-8")
    source, n = re.subn(r"(public static int APP_ID\s*=\s*)[^;]+;", lambda m: m[1]+app_id+";", source)
    source, m = re.subn(r"(public static String APP_HASH\s*=\s*)[^;]+;", lambda x: x[1]+'"'+app_hash+'";', source)
    if (n,m) != (1,1):
        raise SystemExit("Unexpected upstream API declarations")
    path.write_text(source, encoding="utf-8")
    print("Configured API in disposable checkout; do not commit this file.")

if __name__ == "__main__":
    main()
