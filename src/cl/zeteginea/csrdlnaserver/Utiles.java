package cl.zeteginea.csrdlnaserver;

import java.text.NumberFormat;

public class Utiles {

	
	public Utiles() {
		
		
	}

	
	public String formatDuration(long durationInMilliSeconds) {
				
		long seconds = durationInMilliSeconds / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		String duration = hours + ":" + minutes % 60 + ":" + seconds % 60;
		return duration;
	}
	
	public String formatDuration(String durationString) {
		
		long durationInMilliSeconds = Long.parseLong(durationString);
		long seconds = durationInMilliSeconds / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		String duration = this.formatDecimals(hours, 2) + ":" + this.formatDecimals(minutes % 60, 2) + ":" + this.formatDecimals(seconds % 60, 2);
		return duration;
	}
	
	public String formatDecimals(long number, long digits) {
		
		long pow = (long) Math.pow(10, digits);
		String formatted;
		if(pow > number) {
			long ext = (number + pow);
			formatted = Long.toString(ext);
			formatted = formatted.substring(1);
		} else {
			formatted = Long.toString(number);
		}
		
		return formatted;
	}
	
	public String formatSize(String size) {
		
		long durationInBytes = Long.parseLong(size);
		long newDuration;
		
		if(durationInBytes > 999) {
		
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(1);
			
			newDuration = durationInBytes / 1000;
			if(newDuration > 999) {
				newDuration = newDuration / 1000;
				if(newDuration > 999) {
					newDuration = newDuration / 1000;					
					if(newDuration > 999) {
						String output = nf.format(newDuration);
						return output + "TB";
					} else {
						String output = nf.format(newDuration);
						return output + "GB";
					}
				} else {
					String output = nf.format(newDuration);
					return output + "MB";
				}
			} else {
				String output = nf.format(newDuration);
				return output + "kB";
			}
			
		}
		return size + "B";
		
	}
}
