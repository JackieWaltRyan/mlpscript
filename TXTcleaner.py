import os

if __name__ == "__main__":
    path = os.getcwd()
    for root, dirs, files in os.walk(path):
        for file in files:
            if not file.endswith("english.txt") and not file.endswith("russian.txt") and not file.endswith("main.py"):
                filename = os.path.join(root, file)
                print("Удаляем", filename)
                os.remove(filename)
