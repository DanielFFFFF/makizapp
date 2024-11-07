import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import jsQR from 'jsqr';

@Component({
  selector: 'app-scanner',
  templateUrl: './scanner.component.html',
  styleUrls: ['./scanner.component.css']
})
export class ScannerComponent implements OnInit, OnDestroy {
  @ViewChild('video') video!: ElementRef<HTMLVideoElement>;
  @ViewChild('canvas') canvas!: ElementRef<HTMLCanvasElement>;
  private stream: MediaStream | null = null;
  videoUrl: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.startCamera();
  }

  ngOnDestroy(): void {
    this.stopCamera();
  }

  // Starts the camera and initiates the marker scan process
  async startCamera() {
    try {
      this.stream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: 'environment' } });
      this.video.nativeElement.srcObject = this.stream;
      requestAnimationFrame(this.scanMarker.bind(this));
    } catch (error) {
      console.error("Error accessing the camera:", error);
    }
  }

  // Stops the camera when the component is destroyed
  stopCamera() {
    if (this.stream) {
      this.stream.getTracks().forEach(track => track.stop());
      this.stream = null;
    }
  }

  // Continuously scans the video feed for a QR code or marker
  scanMarker() {
    if (this.video.nativeElement.readyState === this.video.nativeElement.HAVE_ENOUGH_DATA) {
      const canvas = this.canvas.nativeElement;
      const context = canvas.getContext('2d');

      if (context) {
        context.drawImage(this.video.nativeElement, 0, 0, canvas.width, canvas.height);
        const imageData = context.getImageData(0, 0, canvas.width, canvas.height);

        // Scans for a QR code
        const code = jsQR(imageData.data, canvas.width, canvas.height);

        if (code) {
          this.fetchVideoData(code.data);
        } else {
          requestAnimationFrame(this.scanMarker.bind(this)); // Continue scanning if no code is found
        }
      }
    } else {
      requestAnimationFrame(this.scanMarker.bind(this));
    }
  }

  // Fetches video URL data from the server based on the scanned marker ID
  fetchVideoData(markerId: string) {
    this.stopCamera();
    this.http.get<{ videoUrl: string }>(`/api/markers/${markerId}`).subscribe(
      response => {
        this.videoUrl = response.videoUrl; // The Vimeo video URL
      },
      error => {
        console.error("Error fetching data:", error);
      }
    );
  }
}
