import sys

# Read the build log from stdin
build_log = sys.stdin.read()

# Define the pattern to search for
pattern = r"java\.io\.IOException: CreateProcess error=\d+, The system cannot find the file specified"

# Search for the pattern in the build log
if pattern in build_log:
    # Print the pattern if found
    print(f"Pattern Found: {pattern}")
    sys.exit(1)  # Exit with a non-zero status code to indicate failure
else:
    print("Pattern Not Found")
    sys.exit(0)  # Exit with a zero status code to indicate success
