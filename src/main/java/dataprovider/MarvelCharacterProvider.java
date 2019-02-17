package dataprovider;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public 	class MarvelCharacterProvider implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		return Stream.of(
				(Object) new String[] { "Spider-Man", "1009610", "3069", "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg" },
				(Object) new String[] { "Hulk", "1009351", "1359", "http://i.annihil.us/u/prod/marvel/i/mg/5/a0/538615ca33ab0.jpg" },
				(Object) new String[] { "Thor", "1009664", "1434", "http://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg" },
				(Object) new String[] { "Iron Man", "1009368", "2240", "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg" },
				(Object) new String[] { "Captain America", "1009220", "1905", "http://i.annihil.us/u/prod/marvel/i/mg/3/50/537ba56d31087.jpg" },
				(Object) new String[] { "Black Widow", "1009189", "398", "http://i.annihil.us/u/prod/marvel/i/mg/f/30/50fecad1f395b.jpg" },
				(Object) new String[] { "Black Panther", "1009187", "503", "http://i.annihil.us/u/prod/marvel/i/mg/6/60/5261a80a67e7d.jpg" },
				(Object) new String[] { "Hawkeye", "1009338", "520", "http://i.annihil.us/u/prod/marvel/i/mg/e/90/50fecaf4f101b.jpg" },
				(Object) new String[] { "Doctor Strange", "1009282", "624", "http://i.annihil.us/u/prod/marvel/i/mg/5/f0/5261a85a501fe.jpg" },
				(Object) new String[] { "Thanos", "1009652", "210", "http://i.annihil.us/u/prod/marvel/i/mg/6/40/5274137e3e2cd.jpg" }).map(Arguments::of);
	}
}
