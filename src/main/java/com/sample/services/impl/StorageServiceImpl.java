package com.sample.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sample.exceptions.StorageException;
import com.sample.exceptions.StorageFileNotFoundException;
import com.sample.properties.StorageProperties;
import com.sample.services.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {
    
	private final Path rootLocation;
	
	@Autowired
    public StorageServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

    @Override
    public void store(MultipartFile file) {
    	String filename = StringUtils.cleanPath(file.getOriginalFilename());
    	try {
    		if (file.isEmpty()) {
    			throw new StorageException("Failed to store empty file" + filename);
    		}
    		if (filename.contains("..")) {
    			throw new StorageException("Cannot store a file with relative path outside current dir " + 
    			filename);
    		}
    		try (InputStream inputStream = file.getInputStream()) {
    			Files.copy(inputStream, this.rootLocation.resolve(filename),
    				StandardCopyOption.REPLACE_EXISTING);
    			
    		}
    	} catch (IOException ex) {
    		throw new StorageException("Failed to store file " + filename, ex);
    	}		
    }
    
    @Override
    public Stream<Path> loadAll() {
    	try {
    		return Files.walk(this.rootLocation, 1)
    			.filter(path -> !path.equals(this.rootLocation))
    			.map(this.rootLocation::relativize);
    	} catch (IOException ex) {
    		throw new StorageException("Failed to read stored files", ex);
    	}
    }
    
    @Override
    public Path load(String filename) {
    	return rootLocation.resolve(filename);
    }
    
    @Override
    public Resource loadAsResource(String filename) {
    	try {
    		Path file = load(filename);
    		Resource resource = new UrlResource(file.toUri());
    		if (resource.exists() || resource.isReadable()) {
    			return resource;
    		} else {
    			throw new StorageFileNotFoundException("Could not read file: " + filename);
    		}
    	} catch (MalformedURLException ex) {
    		throw new StorageFileNotFoundException("Could not read file: " + filename, ex);
    	}
    }
    
    @Override
    public void deleteAll() {
    	FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
    
    @Override
    public void init() {
    	try {
    		Files.createDirectories(rootLocation);
    	} catch (IOException ex) {
            throw new StorageException("Could not initialize storage", ex);
        }
    }
    
}
