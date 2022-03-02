FROM python:3.8

ENV LANG=C.UTF-8
ENV TZ=Asia/Seoul

RUN apt-get update && apt-get install -y --no-install-recommends tzdata g++ git curl
RUN apt-get install -y default-jdk default-jre
RUN apt-get clean && rm -rf /var/lib/apt/lists/*
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /workspace
WORKDIR /workspace

RUN apt-get update && apt-get install -y vim
RUN pip install jpype1-py3 konlpy requests beautifulsoup4
RUN pip install --upgrade tweepy==3.10.0
RUN cd /workspace && curl -s https://raw.githubusercontent.com/konlpy/konlpy/master/scripts/mecab.sh | bash -s 

COPY ./jvm.py /usr/local/lib/python3.8/site-packages/konlpy/jvm.py
COPY . .
RUN python test.py
RUN pip install --no-cache-dir --upgrade -r requirements.txt

EXPOSE 4000
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "4000"]