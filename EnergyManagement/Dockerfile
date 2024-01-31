FROM node:20.1.0 as build

WORKDIR /usr/local/app

# Add the source code to app
COPY ./ /usr/local/app/

# Install all the dependencies
RUN npm install

# Generate the build of the application
RUN npm run build

# Stage 2: Serve app with nginx server
# Use official nginx image as the base image
FROM nginx:latest

# Copy the nginx configuration file
COPY ../../default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/local/app/dist/energy-management /usr/share/nginx/html

# Expose port 80
EXPOSE 4200
