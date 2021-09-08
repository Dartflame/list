FROM java:8

ARG JAR_FILE

RUN mkdir -p /apps
COPY ./target/${JAR_FILE} /apps/app.jar
COPY ./entrypoint.bat /apps/entrypoint.bat
RUN chmod +x /apps/entrypoint.bat

CMD ["/apps/entrypoint.bat"]