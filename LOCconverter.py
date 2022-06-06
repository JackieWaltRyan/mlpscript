from os import getcwd, walk, remove
from os.path import join

if __name__ == "__main__":
    for root, dirs, files in walk(getcwd()):
        for file in files:
            if file.endswith(".loc"):
                filename = join(root, file)
                print(f"Конвертируем: {filename}")
                with open(filename, "rb") as f:
                    data, datapointer, asciiOrUTF, output = f.read(), 4, 1, ""
                    while datapointer < len(data):
                        symcount = data[datapointer:datapointer + 4]
                        symcount = int.from_bytes(symcount, byteorder="little")
                        datapointer += 4
                        tmp = data[datapointer:datapointer + (symcount * asciiOrUTF)]
                        if asciiOrUTF == 1:
                            output += tmp.decode("utf8")
                            datapointer += symcount * asciiOrUTF
                            asciiOrUTF = 2
                        else:
                            tmp = tmp.decode("utf-16-le")
                            if tmp.endswith("\n"):
                                tmp = tmp[:-1]
                            if len(tmp) > 0:
                                output += "=" + tmp + "\n"
                            else:
                                output += "\n"
                            datapointer += symcount * asciiOrUTF
                            asciiOrUTF = 1
                with open(filename[:-4] + ".txt", "wt", encoding="utf-8") as fhw:
                    fhw.write(output[:-1])
                    remove(filename)
