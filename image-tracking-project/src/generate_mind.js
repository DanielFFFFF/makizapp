import { OfflineCompiler } from './offline-compiler.js';
import { URL } from 'url';
import path from 'path';
import { loadImage } from 'canvas';
import { writeFile, readdir } from 'fs/promises';

// Helper function to get the directory name in ES Modules
const __dirname = new URL('.', import.meta.url).pathname;

async function run() {
    // Get the directory path from the command line
    const userProvidedPath = process.argv[2];
    if (!userProvidedPath) {
        console.error("Error: Please provide the directory path as a command-line argument.");
        console.log("Usage: node test.js <directory-path>");
        process.exit(1);
    }

    // Resolve the directory path
    const directoryPath = path.resolve(__dirname, userProvidedPath);
    console.log(`Resolved directory path: ${directoryPath}`);

    try {
        // Read the files in the directory
        const files = await readdir(directoryPath);
        
        // Filter image files (you can adjust this to suit your image types)
        const imageFiles = files.filter(file =>
            ['.png', '.jpg', '.jpeg'].includes(path.extname(file).toLowerCase())
        );

        // Check if there are any images in the directory
        if (imageFiles.length === 0) {
            console.error("No image files found in the directory.");
            process.exit(1);
        }

        // Load all images
        const imagePaths = imageFiles.map(file => path.join(directoryPath, file));
        const images = await Promise.all(imagePaths.map(imagePath => loadImage(imagePath)));

        const compiler = new OfflineCompiler();
        await compiler.compileImageTargets(images, console.log);

        // Specify the output file path within the same directory
        const outputFilePath = path.join(directoryPath, 'targets.mind');
        
        // Export and save the data
        const buffer = compiler.exportData();
        await writeFile(outputFilePath, buffer);
        console.log(`Compilation successful! Output written to '${outputFilePath}'`);
    } catch (error) {
        console.error("Error during image processing:", error.message);
        process.exit(1);
    }
}

run();

