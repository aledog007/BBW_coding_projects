# Use the official Node.js 16 image as a parent image
FROM node:16

# Globally install live-server
RUN npm install -g live-server

# Set the working directory in the container
WORKDIR /usr/src/app

# Make port 9000 available to the world outside this container
EXPOSE 9000

# Run live-server on container start with the specified port
CMD ["live-server", "--port=9000"]
