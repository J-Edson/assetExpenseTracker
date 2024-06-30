# Use an appropriate base image with JDK 17 and basic utilities
FROM openjdk:17-alpine

# Set environment variables
ENV GRAILS_VERSION 5.3.6
ENV GRAILS_HOME /usr/lib/grails
ENV PATH $GRAILS_HOME/bin:$PATH

ENV GRADLE_HOME /usr/lib/gradle
ENV PATH $GRADLE_HOME/bin:$PATH

# Install dependencies
RUN apk update && apk add --no-cache \
    curl \
    unzip

# Download and install Grails
RUN curl -L -o /tmp/grails.zip https://github.com/grails/grails-core/releases/download/v5.3.6/grails-5.3.6.zip \
    && unzip -q /tmp/grails.zip -d /usr/lib \
    && ln -s /usr/lib/grails-${GRAILS_VERSION} $GRAILS_HOME \
    && rm /tmp/grails.zip

# Install Gradle
ENV GRADLE_VERSION 7.2
RUN curl -L https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o /tmp/gradle.zip \
    && unzip -q /tmp/gradle.zip -d /usr/lib \
    && mv /usr/lib/gradle-${GRADLE_VERSION} /usr/lib/gradle \
    && rm /tmp/gradle.zip


# Verify installation
RUN grails --version
RUN gradle --version

# Set the working directory inside the container
WORKDIR /app

# Copy the Grails application files to the container
COPY . .

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application
CMD ["grails", "run-app"]
