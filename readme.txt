EncodingHelper - by Michael Stoneman (stonemanm) and Sam Boswell (boswells)

Our encoding helper's unique feature relies on users being lax with their
input formatting for codepoints and bytes. We eliminated any distinction
between 'U+00EA', 'u00EA', and '\u00EA' etc etc. Same goes for byte
sequences, which can be expressed with or without '\x' or 'x'.

One could test the differences between 'U+0F33', 'u0F33', and '0F33', all
of which correspond to the Tibetan digit half zero, which I am fond of.
As with the half zero, it doesn't matter how the codepoint is expressed,
just that it is.

The user can relax when trying to input UTF8 byte representations too!
All they have to do is input E0BCB3 to refer to the half zero. There is
no need to input '\x' or even 'x', because our encoding helper
understands the stress our user is under.
