BEGIN { ns=0 }

/^--EXPECT--/ { ns=1 }
/.*/ { if(ns) { sub(/^[ \t]+$/, ""); print; } else print; }

